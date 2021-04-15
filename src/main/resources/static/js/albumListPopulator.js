let albumContainer = document.querySelector('#albumListContainer');
let msnAlbum = new Masonry(albumContainer, {
    // options
    percentPosition: true,
    itemSelector: '.msnry-Card'
});

let displayAlbum = albumJSON => {
    let template = document.querySelector('#albumTemplate');
    let clone = template.content.cloneNode(true);
    let node = clone.getElementById('album_ALBUM-ID');
    node.id = 'album' + albumJSON.id;

    // fill in album info
    for (let i = 0; i < 2; i++) {
        node.innerHTML = node.innerHTML.replace("_ALBUM-ID", albumJSON.id);
    }

    node.innerHTML = node.innerHTML.replace("ALBUM NAME", albumJSON.name)
        .replace("_ALBUM-COVER", albumJSON.cover);

    albumContainer.append(node);
    msnAlbum.appended(node);
    msnAlbum.layout();
}

let getAllAlbums = () => {
    fetch(apiURL + 'albums/read').then(res => res.json())
        .then((data) => {
            for (let i = 0; i < data.length; i++) {
                displayAlbum(data[i]);
            }
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

let getAlbumsByArtist = (artist_id) => {
    fetch(apiURL + 'albums/read/artist/' + artist_id).then(res => res.json())
        .then((data) => {
            for (let i = 0; i < data.length; i++) {
                displayAlbum(data[i]);
            }
        })
        .catch((error) => console.error(`Request failed ${error}`))
}