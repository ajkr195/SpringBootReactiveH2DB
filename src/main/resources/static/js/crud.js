var selectedRow = null

function onFormSubmit() {
        var formData = readFormData();
        if (selectedRow == null)
            insertNewRecord(formData);
        else
            updateRecord(formData);
        resetForm();
}

function readFormData() {
    var formData = {};
    formData["todoid"] = document.getElementById("todoid").value;
    formData["todotext"] = document.getElementById("todotext").value;
    formData["todocompleted"] = document.getElementById("todocompleted").value;
    return formData;
}

function insertNewRecord(formData) {
 fetch('/api/todo', {
  method: 'POST',
  headers: {
    'Accept': 'application/json, text/plain, */*',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({id: formData.todoid, text: formData.todotext, completed : formData.todocompleted})
}).then((response) => {
		 if (response.status >= 400 && response.status < 600) {
	    	 throw new Error("Bad response from server");
	     }
	     if (response.status === 200) {
	    	alert('Entry Successful.. ! \nTable will be reloaded now.!');
	    	reloadRecords();
	     }
	 // alert(response.status);
	 })
	 .catch((error) => {
	    	      // Your error is here!
	    	      alert(error)
	  });
}

function resetForm() {
    document.getElementById("todoid").value = "";
    document.getElementById("todotext").value = "";
    document.getElementById("todocompleted").value = "false";
    selectedRow = null;
}

function onEdit(td) {
	//alert(td);
    selectedRow = td.parentElement.parentElement;
    document.getElementById("todoid").value = selectedRow.cells[0].innerHTML;
    document.getElementById("todotext").value = selectedRow.cells[1].innerHTML;
    document.getElementById("todocompleted").value = selectedRow.cells[2].innerHTML;
}
async function updateRecord(formData) {
  await fetch('/api/todo', {
  method: 'PUT',
  headers: {
    'Accept': 'application/json, text/plain, */*',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({id: formData.todoid, text: formData.todotext, completed : formData.todocompleted})
}).then((response) => {
		 if (response.status >= 400 && response.status < 600) {
	    	 throw new Error("Bad response from server");
	     }
	     if (response.status === 200) {
	    	alert('Update Successful.. ! \nTable will be reloaded now.!');
	    	reloadRecords();
	     }
	 // alert(response.status);
	 })
	 .catch((error) => {
	    	      // Your error is here!
	    	      alert(error)
	  });
    
}

function onDelete(td) {
    if (confirm('Are you sure to delete this record ?')) {
        row = td.parentElement.parentElement;
        deleteRecord(row.rowIndex)
        document.getElementById("dataTable").deleteRow(row.rowIndex);
        resetForm();
    }
}


async function deleteRecord(id) {
	
	var url="/api/todo/" + id;
	
	//alert('Selected ID :: ' + id);
	
	if (confirm("Hello, This operation will delete Todo with ID"+ id + ":: Are you sure?")) 
      await fetch(url , { method: 'DELETE',headers: {'Accept': 'application/json'}})
	 
	 .then((response) => {
		 if (response.status >= 400 && response.status < 600) {
	    	 throw new Error("Bad response from server");
	     }
	     if (response.status === 200) {
	    	alert('Delete Successful.. ! \nTable will be reloaded now.!');
	    	reloadRecords();
	     }
	 // alert(response.status);
	 })
	 .catch((error) => {
	    	      // Your error is here!
	    	      alert(error)
	  });
	
	}
	
fetch("/api/todos").then(
				res=>{ res.text().then(
						data=>{
							//alert(data);
							data = JSON.parse(data);
							var trtdVar = "";
							data.forEach((u)=>{
								trtdVar += "<tr>";
								trtdVar += "<td>" + u.id+"</td>";
								trtdVar += "<td>" + u.text+"</td>";
								trtdVar += "<td>" + u.completed+"</td><td><button onclick=\"onEdit(" + `this` + ")\" class=\"btn btn-sm btn-warning mr-3 editBtn\">Edit</button><button onclick=\"deleteRecord(" + u.id + ")\" class=\"btn btn-sm btn-danger mr-3\">X</button></td></tr>";
							})
							
							document.getElementById("appDataTable2").innerHTML = trtdVar;
						})
				}
)
			
			
async function reloadRecords(){
		await fetch("/api/todos").then(
				res=>{ res.text().then(
						data=>{
							//alert(data);
							data = JSON.parse(data);
							var trtdVar = "";
							data.forEach((u)=>{
								trtdVar += "<tr>";
								trtdVar += "<td>" + u.id+"</td>";
								trtdVar += "<td>" + u.text+"</td>";
								trtdVar += "<td>" + u.completed+"</td><td><button onclick=\"onEdit(" + `this` + ")\" class=\"btn btn-sm btn-warning mr-3 editBtn\">Edit</button><button onclick=\"deleteRecord(" + u.id + ")\" class=\"btn btn-sm btn-danger mr-3\">X</button></td></tr>";
							})
							
							document.getElementById("appDataTable2").innerHTML = trtdVar;
						})
				}
			)
}