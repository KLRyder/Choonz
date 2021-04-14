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

    let artistname = trackJSON.artists.length>0?  trackJSON.artists[0].name : "_ARTIST-NAME";
    let artistid = trackJSON.artists.length>0?  trackJSON.artists[0].id : "_ARTIST-ID";

    node.innerHTML = node.innerHTML.replace("_ARTIST-ID", artistid)
        .replace("_ARTIST-NAME", artistname)
        .replace("_ALBUM-ID", trackJSON.album.id)
        .replace("_ALBUM-NAME", trackJSON.album.name)
        .replace("track name", trackJSON.name)
        .replace("_GENRE-ID", trackJSON.genre.id)
        .replace("_GENRE-NAME", trackJSON.genre.name)


    document.getElementById('trackListAccordion').append(node);
}

let getAllTracks = () => {
    console.log("try to populate")
    fetch(apiURL + 'tracks/read').then(res => res.json())
        .then((data) => {
            for (let i = 0; i < data.length; i++) {
                populate(data[i]);
            }
        })
        .catch((error) => console.error(`Request failed ${error}`))
}