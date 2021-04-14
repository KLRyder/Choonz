function createPlaylist(){
    let playlistName = document.querySelector('#create-playlist-name').value;
    let playlistDesc = document.querySelector('#create-playlist-desc').value;
    let playlistPic = document.querySelector('#playlistPic').value;


    fetch(apiURL + "playlists/create", {
    method: 'post',
    headers: {
        "Content-type": "application/json"
    },
    body: JSON.stringify({
        "name": playlistName,
        "description": playlistDesc,
        "artwork": playlistPic
    })
}).then(res => res.json())
    .then((data) => {
        return;
    })
    .catch((error) => console.error(`Request failed ${error}`))
}