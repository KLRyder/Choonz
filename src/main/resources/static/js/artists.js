function deleteArtist(){
    fetch(apiURL + "artists/delete/" + urlParams.get("artist_id"), {
        method: 'delete'
    }).then(res => {
        if (res.status === 200) {
            console.info("Deleted successfully")
            return;
        } else {
            console.error(`Request failed ${res.body}`)
        }
    }).catch((error) => console.error(`Request failed ${error}`))
}

function updateArtist(){
    let artistName = document.querySelector('#update-artist-name').value;

    fetch(apiURL + "artists/update/" + urlParams.get("artist_id"), {
        method: 'post',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "name": artistName
        })
    }).then(res => res.json())
        .then((data) => {
            console.info("Updated")
            return;
        })
         .catch((error) => console.error(`Request failed ${error}`))
}

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