//GET
await (await fetch("/api/todo", {
  method: "get",
  headers: {
    'Accept': 'application/json'
   }
})).json()
//POST
await (await fetch("/api/todo", {
  method: "post",
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },

  //make sure to serialize your JSON body
  body: JSON.stringify({
    text: "Todo1",
    completed: false
  })
})).json()

//PUT
await (await fetch("/api/todo", {
  method: "put",
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },

  //make sure to serialize your JSON body
  body: JSON.stringify({
    id: 1, 
    text: "Blablahblah",
    completed: true
  })
})).json()
//DELETE
await (await fetch("/api/todo/1", {
  method: "delete",
  headers: {
    'Accept': 'application/json'
   }
})).json()