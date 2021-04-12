function deleteAlbum(){
    if (dataType.value == "listdata") {
        fetch("http://localhost:8080/todolist/" + id.value, {
            method: 'delete'
        }).then(res => {
            if (res.status === 200) {
                console.info(id.value + " deleted")
                querySuccess();
                return;
            } else {
                console.error(`Request failed ${res.body}`)
            }
        }).catch((error) => console.error(`Request failed ${error}`))
    }
    else if (dataType.value == "itemdata"){
        fetch("http://localhost:8080/item/" + id.value, {
            method: 'delete'
        }).then(res => {
            if (res.status === 200) {
                console.info(id.value + " deleted")
                querySuccess();
                return;
            } else {
                console.error(`Request failed ${res.body}`)
            }
        }).catch((error) => console.error(`Request failed ${error}`))
    }
    queryFailure();
}

function updateAlbum(){
    clearInfo();
    if (dataType.value == "listdata") {
        fetch("http://localhost:8080/todolist/" + id.value, {
            method: 'put',
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify({
                "title": textData.value,
                "completed": document.getElementById("myCheck").checked
            })
        }).then(res => res.json())
            .then((data) => {
                displayList(data);
                querySuccess();
                return;
            })
            .catch((error) => console.error(`Request failed ${error}`))
    }
    else if (dataType.value == "itemdata"){
        fetch("http://localhost:8080/item/" + id.value, {
            method: 'put',
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify({
                "textBody": textData.value,
                "taskDone": document.getElementById("myCheck").checked,
                "toDoList": {
                    "listId": listId.value
                }
            })
        }).then(res => res.json())
            .then((data) => {
                querySuccess();
                return;
            })
            .catch((error) => console.error(`Request failed ${error}`))
    }
    queryFailure();
}