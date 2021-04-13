let artistContainer = document.querySelector('#artistListContainer');
let msn = new Masonry(artistContainer, {
    // options
    percentPosition: true,
    itemSelector: '.msnry-Card'
});

let displayArtist = artistJSON => {
    let template = document.querySelector('#artistTemplate');
    let clone = template.content.cloneNode(true);
    let node = clone.getElementById('album_ALBUM-ID');
    node.id = 'artist' + artistJSON.id;

    // fill in artist info
    for (let i = 0; i < 2; i++) {
        node.innerHTML = node.innerHTML.replace("_ARTIST-ID", artistJSON.id);
    }

    node.innerHTML = node.innerHTML.replace("ARTIST NAME", artistJSON.name);

    artistContainer.append(node);
    msn.appended(node);
    msn.layout();
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