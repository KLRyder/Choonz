function deleteTrack(){
    fetch(apiURL + "tracks/delete/" + urlParams.get("track_id"), {
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

function updateTrack(){
    let trackName = document.querySelector('#update-track-name').value;
    let trackAlbumId = document.querySelector('#update-album-id').value;
    let trackGenreId = document.querySelector('#update-genre-id').value;
    let trackDuration = document.querySelector('#update-track-duration').value;
    let trackLyrics = document.querySelector('#lyricsTextBox').value;

    fetch(apiURL + "tracks/update/" + urlParams.get("track_id"), {
        method: 'post',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "name": trackName,
            "genre": {
              "id": trackGenreId
            },
            "album": {
              "id": trackAlbumId
            },
            "duration": trackDuration,
            "lyrics": trackLyrics
        })
    }).then(res => res.json())
        .then((data) => {
            console.info("Updated")
            return;
        })
         .catch((error) => console.error(`Request failed ${error}`))
}