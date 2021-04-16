function createTrack(){
    console.info("Creating Track")

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
    console.info("Creating Album")

    let albumName = document.querySelector('#create-album-name').value;
    let albumPic = document.querySelector('#albumPic').value;

    fetch(apiURL + "albums/create", {
    method: 'post',
    headers: {
        "Content-type": "application/json"
    },
    body: JSON.stringify({
        "name": albumName,
        "cover": albumPic
    })
}).then(res => res.json())
    .then((data) => {
        return;
    })
    .catch((error) => console.error(`Request failed ${error}`))
}

function createArtist(){
    console.info("Creating Artist")

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
    console.info("Creating genre")

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
