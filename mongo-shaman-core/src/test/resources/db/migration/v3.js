db.shaman_test.updateOne({ item: "card" },{ $inc: { qty: 1 }});
db.shaman_test.insertOne( { item: "magic", qty: NumberInt(0) } );
db.shaman_test.updateOne({ item: "magic" },{ $inc: { qty: -1 }});
db.shaman_test.deleteOne({ item: "card" });
