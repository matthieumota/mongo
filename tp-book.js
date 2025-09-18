// @todo exercice trouver tous les étudiants ayant un score entre 6 et 86 pour tous les scores
db.students.find({
    scores: {
      $all: [
        { $elemMatch: { score: { $gt: 6, $lte: 86 } } },
      ]
    }
  },
  { name: 1, scores: 1 }
)

// @todo voir si on peut avoir le .0 en .N
db.students.find({
    'scores.0.score': { $gt: 6, $lte: 86 },
    'scores.1.score': { $gt: 6, $lte: 86 },
    'scores.2.score': { $gt: 6, $lte: 86 }
  },
  { name: 1, scores: 1 }
)

// Créer la base de données et la collection
use tp
db.createCollection('books')

// Créer un premier document
db.books.insertOne({
  type: 'Book',
  title: 'Symfony 5: The Fast Track.',
  year: 2019,
  publisher: 'SensioLabs',
  authors: ['Fabien Potencier'],
  source: 'Symfony'
})

// Insérer deux autres livres de votre choix avec la même structure
db.books.insertMany([
  {
    type: 'Book',
    title: 'Laravel 12',
    year: 2025,
    publisher: 'Laravel',
    authors: ['Taylor Otwell'],
    source: 'Laravel'
  },
  {
    type: 'Document',
    title: 'Rust',
    year: 2024,
    publisher: 'Rust',
    authors: ['Don\'t know'],
    source: 'Rust'
  }
])

// Consulter votre collection avec la bonne requête
db.books.find()

// Importer les données du zip fourni par le formateur
cat ~/Downloads/books.json | docker exec -i mongo mongoimport --db tp --collection books --jsonArray

// Lister tous les livres (Books)
db.books.find({ type: 'Book' })

// Lister les documents depuis 2000
db.books.find({ year: { $gte: 2000 } })

// Lister les livres depuis 2010
db.books.find({ type: { $eq: 'Book' }, year: { $gte: 2010 } })

// Lister les documents de l'auteur Michael Schmitz
db.books.find({ authors: { $in: ['Michael Schmitz'] } })

// Lister les éditeurs
db.books.distinct('publisher')

// Lister les auteurs
db.books.distinct('authors')

// Trier les documents de Michael Schmitz par titre et par page de début
db.books.aggregate([
  { $match: { authors: 'Michael Schmitz' } },
  { $sort: { title: 1, 'pages.start': 1 } }
])

// Afficher uniquement le titre et les pages en résultat (Avec la projection)
db.books.aggregate([
  { $match: { authors: 'Michael Schmitz' } },
  { $sort: { title: 1, 'pages.start': 1 } },
  { $project: { _id: 0, title: 1, 'pages.start': 1 } }
])

// Compter le nombre de ses documents
// db.books.find({ authors: 'Michael Schmitz' }).count()
db.books.aggregate([
  { $match: { authors: 'Michael Schmitz' } },
  { $group: { _id: null, total: { $sum: 1 } } }
])

// Compter le nombre de documents depuis 2000 et par type
db.books.aggregate([
  { $match: { year: { $gte: 2000 } } },
  { $group: { _id: '$type', total: { $sum: 1 } } },
])

// Compter le nombre de documents par auteur et trier le résultat par ordre décroissant
db.books.aggregate([
  { $unwind: '$authors' },
  { $group: { _id: '$authors', total: { $sum: 1 } } },
  { $sort: { total: -1 } }
])

// Pour chaque document qui est un livre, émettre le document comme valeur avec comme clé son année
// On appliquera ensuite un reduce pour avoir le nombre de livres par année
db.books.mapReduce(
  function () {
    if ('Book' === this.type) {
      emit(this.year, this)
    }
  },
  function (key, values) {
    return values.length + ' livre(s) en ' + key
  },
  {
    out: { inline: 1 }
  }
)

db.books.aggregate([
  { $match: { type: 'Book' } },
  { $group: {
    _id: '$year',
    count: { $sum: 1 }
  }},
  { $project: {
    _id: 0,
    year: '$_id',
    message: { $concat: [
      { $toString: '$count' }, ' livre(s) en ', { $toString: '$_id' }
    ] }
  } },
  { $sort: { _id: 1 } }
])

// Compter le nombre d'auteurs pour chacun de ces livres par année
db.books.mapReduce(
  function () {
    if ('Book' === this.type) {
      emit(this.year, this.authors.length)
    }
  },
  function (key, values) {
    return Array.sum(values)
  },
  {
    out: { inline: 1 }
  }
)

db.books.aggregate([
  { $match: { type: 'Book' } },
  { $unwind: '$authors' },
  { $group: {
    _id: '$year',
    total: { $sum: 1 }
  }},
  { $sort: { _id: 1 } }
])

db.books.aggregate([
  {$match: {type: {"$eq": "Book"}}},
  {$project: { year: 1, total: {$size: "$authors"} }},
  {$group: {_id: "$year", total: {$sum: "$total"}}},
  {$sort: {total: -1}},
])
