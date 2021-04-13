function addPlaylistTrack(){
    let trackId = document.querySelector('#add-playlist-track').value;
    console.info("This is track ID:" + trackId)
    console.info("This is playlist ID:" + urlParams.get("playlist_id"))

    fetch(apiURL + "playlists/add/" + urlParams.get("playlist_id") + "/" + trackId, {
        method: 'put',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
        })
    }).then(res => res.json())
        .then((data) => {
            return;
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

function removePlaylistTrack(){
    let trackId = document.querySelector('#remove-playlist-track').value;
    console.info("This is track ID:" + trackId)
    console.info("This is track ID:" + urlParams.get("playlist_id"))

    fetch(apiURL + "playlists/remove/" + urlParams.get("playlist_id") + "/" + trackId, {
        method: 'put',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
        })
    }).then(res => res.json())
        .then((data) => {
            return;
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

function deletePlaylist(){
    fetch(apiURL + "playlists/delete/" + urlParams.get("playlist_id"), {
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
    let playlistArt = document.querySelector('#playlistPic').value;
    let playlistDesc = document.querySelector('#update-playlist-desc').value;

    fetch(apiURL + "playlists/update/" + urlParams.get("playlist_id"), {
        method: 'post',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "name": playlistName,
            "description": playlistDesc,
            "artwork": playlistArt
        })
    }).then(res => res.json())
        .then((data) => {
            console.info("Updated")
            return;
        })
         .catch((error) => console.error(`Request failed ${error}`))
}

let fill = (playlistJSON) => {

    let playlistNameText = document.querySelector('#playlistName');
    playlistNameText.innerHTML = playlistNameText.innerHTML.replace("PLAYLIST NAME", playlistJSON.name)

    document.getElementById("playlistDesc").innerHTML = playlistJSON.description;

    let playlistImage = document.getElementById("image1");
    playlistImage.src = playlistJSON.artwork;
    console.info(playlistJSON.artwork)

    console.log(playlistJSON)
    for (let i =0;i<playlistJSON.tracks.length;i++) {
        populate(playlistJSON.tracks[i]);
    }
    

}

let playlistInfoFill = (playlistId) => {
    fetch(apiURL + 'playlists/read/' + playlistId).then(res => res.json())
        .then((data) => {
            fill(data);
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

playlistInfoFill(urlParams.get("playlist_id"));