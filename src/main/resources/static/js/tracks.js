'use strict'

let fill = (trackJSON) => {

    console.log("Try to fill in information")
    let trackNameText = document.querySelector('#trackName');
    trackNameText.innerHTML = trackNameText.innerHTML.replace("TRACK NAME", trackJSON.name)

    let basicTrackInfo = document.getElementById("trackInfoRow");
    basicTrackInfo.innerHTML = basicTrackInfo.innerHTML
        .replace("_ALBUM-ID", trackJSON.album.id)
        .replace("_ALBUM-NAME", trackJSON.album.name)
        .replace("_DURATION", trackJSON.duration)
        .replace("_GENRE-ID", trackJSON.genre.id)
        .replace("_GENRE-NAME", trackJSON.genre.name)

    let artists = document.getElementById("artistLinks");
    for (let i = 0; i < trackJSON.artists.length; i++) {
        let a = document.createElement('a');
        a.setAttribute('href', "/artists?artist_id=" + trackJSON.artists[i].id)
        a.innerHTML = trackJSON.artists[i].name;
        artists.append(a)
    }

    let fullLyrics = trackJSON.lyrics;
    let splitLyrics = fullLyrics.split(".");

    for (let i = 0; i < ((splitLyrics.length) - 1); i++) {
        splitLyrics[i] = splitLyrics[i] + "<br>";
    }

    splitLyrics = splitLyrics.join("");

    document.getElementById("lyricsBox").innerHTML = splitLyrics;

}

let trackInfoFill = (trackId) => {
    console.log("try to read track by Id")
    fetch(apiURL + 'tracks/read/' + trackId).then(res => res.json())
        .then((data) => {
            fill(data);
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

console.log("try to run script")
trackInfoFill(urlParams.get("track_id"));

function deleteTrack(){
    console.info("Deleting track")

    fetch(apiURL + "tracks/delete/" + urlParams.get("track_id"), {
        method: 'delete'
    }).then(res => {
        if (res.status === 204) {
            window.location.href="/";
        } else {
            console.error(`Request failed ${res.body}`)
        }
    }).catch((error) => console.error(`Request failed ${error}`))
}

function updateTrack(){
    console.info("Updating track")

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
        .then(() => {
            location.reload();
        })
         .catch((error) => console.error(`Request failed ${error}`))
}