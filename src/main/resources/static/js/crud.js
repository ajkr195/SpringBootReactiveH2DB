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
		body: JSON.stringify({ id: formData.todoid, text: formData.todotext, completed: formData.todocompleted })
	})
	
	.then(response => response.json())
	.then(data => {
	        var table = document.getElementById("dataTable").getElementsByTagName('tbody')[0];
			var newRow = table.insertRow(table.length);
			cell1 = newRow.insertCell(0);
			cell1.innerHTML = data.id;
			cell2 = newRow.insertCell(1);
			cell2.innerHTML = data.text;
			cell3 = newRow.insertCell(2);
			cell3.innerHTML = data.completed;
			cell4 = newRow.insertCell(3);
			cell4.innerHTML = `<a class="btn btn-sm btn-warning mr-3" onClick="onEdit(this)">Edit</a>
                       <a class="btn btn-sm btn-danger" onClick="onDelete(this)">X</a>`;
            alert("Entry Successful.. ! New Todo's ID is - " + data.id );
	
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
	selectedRow.cells[1].innerHTML = formData.todotext;
	selectedRow.cells[2].innerHTML = formData.todocompleted;
	await fetch('/api/todo', {
		method: 'PUT',
		headers: {
			'Accept': 'application/json, text/plain, */*',
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ id: formData.todoid, text: formData.todotext, completed: formData.todocompleted })
	}).then((response) => {
		if (response.status >= 400 && response.status < 600) {
			throw new Error("Error response from server !!");
		}
		if (response.status === 200) {
			alert('Update Successful.. !');
			//alert('Update Successful.. ! \nTable will be reloaded now.!');
			//reloadRecords();
		}
		// alert(response.status);
	})
		.catch((error) => {
			// Your error is here!
			alert(error)
		});



}

function onDelete(td) {
	var table = document.getElementById("dataTable");
	if (confirm('Are you sure to delete this record ?')) {
		row = td.parentElement.parentElement;
		getTheInfamousId(table);
		table.deleteRow(row.rowIndex);
		resetForm();
	}
}

function getTheInfamousId(table) {
	var rows = table.getElementsByTagName("tr");
	for (i = 0; i < rows.length; i++) {
		var currentRow = table.rows[i];
		var createClickHandler = function(row) {
			return function() {
				var cell = row.getElementsByTagName("td")[0];
				var thisIsTheInfamousId = cell.innerHTML;
				deleteRecord(thisIsTheInfamousId);
			};
		};
		currentRow.onclick = createClickHandler(currentRow);
	}
}


async function deleteRecord(id) {
	var url = "/api/todo/" + id;
	if (confirm("Hello, This operation will delete Todo with ID" + id + ":: Are you sure?"))
		await fetch(url, { method: 'DELETE', headers: { 'Accept': 'application/json' } })
			.then((response) => {
				if (response.status >= 400 && response.status < 600) {
					throw new Error("Bad response from server");
				}
				if (response.status === 200) {
					alert('Deletion Successful.. !');
					//reloadRecords();
				}
				// alert(response.status);
			})
			.catch((error) => {
				// Your error is here!
				alert(error)
			});

}


fetch("/api/todos").then(
	res => {
		res.text().then(
			data => {
				//alert(data);
				data = JSON.parse(data);
				var trtdVar = "";
				data.forEach((u) => {
					trtdVar += "<tr>";
					trtdVar += "<td>" + u.id + "</td>";
					trtdVar += "<td>" + u.text + "</td>";
					trtdVar += "<td>" + u.completed + "</td><td><button onclick=\"onEdit(" + `this` + ")\" class=\"btn btn-sm btn-warning mr-3 editBtn\">Edit</button><button onclick=\"onDelete(" + `this` + ")\" class=\"btn btn-sm btn-danger mr-3\">X</button></td></tr>";
				})
				document.getElementById("appDataTable2").innerHTML = trtdVar;
			})
	}
)


async function reloadRecords() {
	await fetch("/api/todos").then(
		res => {
			res.text().then(
				data => {
					//alert(data);
					data = JSON.parse(data);
					var trtdVar = "";
					data.forEach((u) => {
						trtdVar += "<tr>";
						trtdVar += "<td>" + u.id + "</td>";
						trtdVar += "<td>" + u.text + "</td>";
						trtdVar += "<td>" + u.completed + "</td><td><button onclick=\"onEdit(" + `this` + ")\" class=\"btn btn-sm btn-warning mr-3 editBtn\">Edit</button><button onclick=\"onDelete(" + `this` + ")\" class=\"btn btn-sm btn-danger mr-3\">X</button></td></tr>";
					})

					document.getElementById("appDataTable2").innerHTML = trtdVar;
				})
		}
	)
}