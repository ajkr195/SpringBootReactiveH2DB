function loadTodos () {

    this.source = null;

    this.start = function () {

        var commentTable = document.getElementById("todos");

        this.source = new EventSource("/api/todos/sse");

        this.source.addEventListener("message", function (event) {

            // These events are JSON, so parsing and DOM fiddling are needed
            var todo = JSON.parse(event.data);
            
            var row = commentTable.getElementsByTagName("tbody")[0].insertRow(0);
            var cell0 = row.insertCell(0);
            var cell1 = row.insertCell(1);
            var cell2 = row.insertCell(2);

            cell0.className = "id";
            cell0.innerHTML = todo.id;

            cell1.className = "text";
            cell1.innerHTML = todo.text;

            cell2.className = "completed";
            cell2.innerHTML = todo.completed;

        });

        this.source.onerror = function () {
            this.close();
        };

    };

    this.stop = function() {
        this.source.close();
    }

}

todo = new loadTodos();

/*
 * Register callbacks for starting and stopping the SSE controller.
 */
window.onload = function() {
    todo.start();
};
window.onbeforeunload = function() {
    todo.stop();
}