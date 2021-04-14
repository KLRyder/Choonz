function createTrack(){
    let trackName = document.querySelector('#create-track-name').value;
    let genreId = document.querySelector('#create-genre-id').value;
    let albumId = document.querySelector('#create-album-id').value;
    let duration = document.querySelector('#create-track-duration').value;
    let lyrics = document.querySelector('#lyricsTextBox').value;

    fetch(apiURL + "tracks/create", {
    method: 'post',
    headers: {
        "Content-type": "application/json"
    },
    body: JSON.stringify({
        "name": trackName,
        "genre": {
          "id": genreId
        },
        "album": {
          "id": albumId
        },
        "duration": duration,
        "lyrics": lyrics
    })
}).then(res => res.json())
    .then((data) => {
        return;
    })
    .catch((error) => console.error(`Request failed ${error}`))
}

function createAlbum(){
    let albumName = document.querySelector('#create-album-name').value;
    let artistId = document.querySelector('#create-album-artistid').value;
    let albumPic = document.querySelector('#albumPic').value;

    fetch(apiURL + "albums/create", {
    method: 'post',
    headers: {
        "Content-type": "application/json"
    },
    body: JSON.stringify({
        "name": albumName,
        "artist": {
          "id": artistId
        },
        "cover": albumPic
    })
}).then(res => res.json())
    .then((data) => {
        return;
    })
    .catch((error) => console.error(`Request failed ${error}`))
}

function createArtist(){
    let artistName = document.querySelector('#create-artist-name').value;

    fetch(apiURL + "artists/create", {
    method: 'post',
    headers: {
        "Content-type": "application/json"
    },
    body: JSON.stringify({
        "name": artistName
    })
}).then(res => res.json())
    .then((data) => {
        return;
    })
    .catch((error) => console.error(`Request failed ${error}`))
}

function createGenre(){
    let genreName = document.querySelector('#create-genre-name').value;
    let genreDesc = document.querySelector('#create-genre-desc').value;

    fetch(apiURL + "genres/create", {
    method: 'post',
    headers: {
        "Content-type": "application/json"
    },
    body: JSON.stringify({
        "name": genreName,
        "description": genreDesc
    })
}).then(res => res.json())
    .then((data) => {
        return;
    })
    .catch((error) => console.error(`Request failed ${error}`))
}