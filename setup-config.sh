mongosh --host mongo-config1:27017 --eval '
    rs.initiate({
        _id: "config",
        configsvr: true,
        members: [
            { _id: 0, host: "mongo-config1:27017" }
        ]
    })
'

echo OK
