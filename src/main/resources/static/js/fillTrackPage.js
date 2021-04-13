'use strict'

let fill = (trackJSON) => {

    console.log("Try to fill in information")
    let trackNameText = document.querySelector('#trackName');
    trackNameText.innerHTML = trackNameText.innerHTML.replace("TRACK NAME", trackJSON.name)

    let basicTrackInfo = document.getElementById("trackInfoRow");
    basicTrackInfo.innerHTML = basicTrackInfo.innerHTML.replace("_ARTIST-ID", trackJSON.artist.id)
        .replace("_ARTIST-NAME", trackJSON.artist.name)
        .replace("_ALBUM-ID", trackJSON.album.id)
        .replace("_ALBUM-NAME", trackJSON.album.name)
        .replace("_DURATION", trackJSON.duration)
    // .replace("_GENRE-ID", trackJSON.genre.id)
    // .replace("_GENRE-NAME", trackJSON.genre.name)

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

trackInfoFill(urlParams.get("track_id"));