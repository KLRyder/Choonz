'use strict'

let displayArtist = artistJSON => {
    let template = document.querySelector('#artistTemplate');
    let artistClone = template.content.cloneNode(true);

    let node = artistClone.getElementById("artist_ARTIST-ID");
    node.id = 'artist-' + artistJSON.id;

    for (let i = 0; i < 7; i++) {
        node.innerHTML = node.innerHTML.replace("_ARTIST-ID", artistJSON.id);
    }

    let artistname = artistJSON.name;
    let artistid = artistJSON.id;

    node.innerHTML = node.innerHTML.replace("_ARTIST-ID", artistid)
        .replace("_ARTIST-NAME", artistname).replace("artist name", artistname)

    document.getElementById('artistListAccordion').append(node);
}

let getAllArtists = () => {
    fetch(apiURL + 'artists/read').then(res => res.json())
        .then((data) => {
            for (let i = 0; i < data.length; i++) {
                displayArtist(data[i]);
            }
        })
        .catch((error) => console.error(`Request failed ${error}`))
}