let playlistContainer = document.querySelector('#playlistContainer');
let msn = new Masonry(playlistContainer, {
    // options
    percentPosition: true,
    itemSelector: '.msnry-Card'
});

let displayPlaylist = playlistJSON => {
    let template = document.querySelector('#playlistTemplate');
    let clone = template.content.cloneNode(true);
    let node = clone.getElementById('playlist_PLAYLIST-ID');
    node.id = 'playlist' + playlistJSON.id;

    // fill in playlist info
    for (let i = 0; i < 2; i++) {
        node.innerHTML = node.innerHTML.replace("_PLAYLIST-ID", playlistJSON.id);
    }

    node.innerHTML = node.innerHTML.replace("PLAYLIST NAME", playlistJSON.name);

    playlistContainer.append(node);
    msn.appended(node);
    msn.layout();
}

let getAllPlaylists = () => {
    fetch(apiURL + 'playlists/read').then(res => res.json())
        .then((data) => {
            for (let i = 0; i < data.length; i++) {
                displayPlaylist(data[i]);
            }
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

let getPlaylistByUser = (user_id) => {
    fetch(apiURL + 'playlists/read/user/' + user_id).then(res => res.json())
        .then((data) => {
            for (let i = 0; i < data.length; i++) {
                displayPlaylist(data[i]);
            }
        })
        .catch((error) => console.error(`Request failed ${error}`))
}