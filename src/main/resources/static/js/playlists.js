function addPlaylistTrack(){
    let trackId = document.querySelector('#add-playlist-track').value;

    fetch("http://localhost:8082/playlists/add/" + urlParams.get("playlist_id") + "/" + trackId.value, {
        method: 'put',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
        })
    }).then(res => res.json())
        .then((data) => {
            displayList(data);
            querySuccess();
            return;
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

function removePlaylistTrack(){
    let trackId = document.querySelector('#remove-playlist-track').value;

    fetch("http://localhost:8082/playlists/remove/" + urlParams.get("playlist_id") + "/" + trackId.value, {
        method: 'put',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
        })
    }).then(res => res.json())
        .then((data) => {
            displayList(data);
            querySuccess();
            return;
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

function deletePlaylist(){
    fetch("http://localhost:8082/playlists/delete/" + urlParams.get("playlist_id"), {
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

function updatePlaylist(){
    let playlistName = document.querySelector('#update-playlist-name').value;
    let playlistArt = document.querySelector('#update-playlist-art').value;
    let playlistDesc = document.querySelector('#update-playlist-desc').value;

    fetch("http://localhost:8082/playlists/update/" + urlParams.get("playlist_id"), {
        method: 'put',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "name": playlistName,
            "description": playlistArt,
            "artwork": playlistDesc
        })
    }).then(res => res.json())
        .then((data) => {
            console.info("Updated")
            return;
        })
         .catch((error) => console.error(`Request failed ${error}`))
}