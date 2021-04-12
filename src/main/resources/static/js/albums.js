let updateAlbumModal = document.getElementById('updateAlbumModal');

updateAlbumModal.addEventListener('show.bs.modal', function (event) {

    //Fix code here to fill in update modal
    //get task information from triggering button
    //let button = event.relatedTarget;
    //let listId = button.getAttribute('data-bs-listID');
    //let listName = button.getAttribute('data-bs-listName');

    // Update the modal's content.
    //let modalTitle = createTaskModal.querySelector('.modal-title');
    //let modelListId = createTaskModal.querySelector('#listID');

    //modalTitle.textContent = 'New task: ' + listName;
    //modelListId.value = listId;
})


function deleteAlbum(){
    fetch("http://localhost:8082/albums/delete/" + urlParams.get("album_id"), {
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

function updateAlbum(){
    let albumName = document.querySelector('#update-album-name').value;
    let albumArtistId = document.querySelector('#update-artist-id').value;
    let albumCover = document.querySelector('#albumPic').value;

    fetch("http://localhost:8082/albums/update/" + urlParams.get("album_id"), {
        method: 'put',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "name": albumName,
            "artist": {
              "id": albumArtistId
            },
            "cover": albumCover
        })
    }).then(res => res.json())
        .then((data) => {
            console.info("Updated")
            return;
        })
         .catch((error) => console.error(`Request failed ${error}`))
}