'use strict'

let populate = (trackJSON) => {
    console.log("try to populate with")
    console.log(trackJSON)
    let template = document.querySelector('#trackTemplate');
    console.log(template)
    let trackClone = template.content.cloneNode(true);

    let node = trackClone.getElementById("track_TRACK-ID");
    node.id = 'track-' + trackJSON.id;

    for (let i = 0; i < 7; i++) {
        node.innerHTML = node.innerHTML.replace("_TRACK-ID", trackJSON.id);
    }

    node.innerHTML = node.innerHTML.replace("_ARTIST-ID", trackJSON.artist.id)
        .replace("_ARTIST-NAME", trackJSON.artist.name)
        .replace("_ALBUM-ID", trackJSON.album.id)
        .replace("_ALBUM-NAME", trackJSON.album.name)
        .replace("track name", trackJSON.name)
    // .replace("_GENRE-ID", trackJSON.genre.id)
    // .replace("_GENRE-NAME", trackJSON.genre.name)

                 
    document.getElementById('trackListAccordion').append(node);
}

let getAllTracks = (albumId) => {
    console.log("try to populate")

    fetch(apiURL + 'albums/read/' + albumId).then(res => res.json())
        .then((data) => {
            for (let i = 0; i < data.tracks.length; i++) {
                populate(data.tracks[i]);
            }
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

console.log("try to run script")
const queryString3 = window.location.search;
const urlParams3 = new URLSearchParams(queryString3);
getAllTracks(urlParams3.get("album_id"));