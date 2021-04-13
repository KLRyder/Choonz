function deleteGenre(){
    fetch("http://localhost:8082/genres/delete/" + urlParams.get("genre_id"), {
        method: 'delete'
    }).then(res => {
        if (res.status === 200) {
            console.info("Deleted successfully")
            return;
        } else {
            console.error(`Request failed ${res.body}`)
        }
    }).catch((error) => console.error(`Request failed ${error}`))
}

function updateGenre(){
    let genreName = document.querySelector('#update-genre-name').value;

    fetch("http://localhost:8082/genres/update/" + urlParams.get("genre_id"), {
        method: 'put',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "name": genreName
        })
    }).then(res => res.json())
        .then((data) => {
            console.info("Updated")
            return;
        })
         .catch((error) => console.error(`Request failed ${error}`))
}