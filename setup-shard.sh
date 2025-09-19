mongosh --host mongo-router:27017 --eval '
    sh.addShard("fiorella/mongo1:27017")
    sh.addShard("marina/mongo4:27017")
'

echo OK
