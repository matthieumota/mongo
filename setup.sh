mongosh --host mongo1:27017 --eval '
    rs.initiate({
        _id: "fiorella",
        members: [
            { _id: 0, host: "mongo1:27017" },
            { _id: 1, host: "mongo2:27017" },
            { _id: 2, host: "mongo3:27017" }
        ]
    })
'

mongosh --host mongo4:27017 --eval '
    rs.initiate({
        _id: "marina",
        members: [
            { _id: 0, host: "mongo4:27017" }
        ]
    })
'

echo OK
