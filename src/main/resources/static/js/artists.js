let displayArtist = (artistJSON) => {
    document.getElementById('artistName').innerText = artistJSON.name;
    console.log(artistJSON);
    for (let i = 0; i < artistJSON.albums.length; i++) {
        console.log(artistJSON.albums[i]);
        displayAlbum(artistJSON.albums[i]);
    }
}

let getArtist = (artist_id) => {
    fetch(apiURL + 'artists/read/' + artist_id).then(res => res.json())
        .then((data) => {
            displayArtist(data);
        })
        .catch((error) => {
            console.log(error)
        })
}

if (urlParams.has("artist_id")) {
    getArtist(urlParams.get("artist_id"));
} else {
    document.getElementById('artistName').innerText = "Invalid artist 😢";
}