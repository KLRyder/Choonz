function deleteAlbum(){
    console.info("Deleting Album")

    fetch(apiURL + "albums/delete/" + urlParams.get("album_id"), {
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
console.info("Updating Album")

    let albumName = document.querySelector('#update-album-name').value;
    let artistId = document.querySelector('#update-album-artistid').value;
    let albumCover = document.querySelector('#albumPic').value;

    fetch(apiURL + "albums/update/" + urlParams.get("album_id"), {
        method: 'post',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "name": albumName,
            "artist": {
              "id": artistId
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