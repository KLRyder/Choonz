'use strict'

function addAlbumArtist() {
    let artistId = document.querySelector('#add-album-artist').value;

    fetch(apiURL + "albums/add/" + urlParams.get("album_id") + "/" + artistId, {
        method: 'put',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({})
    }).then(res => res.json())
        .then(() => {
            location.reload();
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

function removeAlbumArtist() {
    let artistId = document.querySelector('#remove-album-artist').value;

    fetch(apiURL + "albums/remove/" + urlParams.get("album_id") + "/" + artistId, {
        method: 'put',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({})
    }).then(res => res.json())
        .then(() => {
            location.reload();
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

function deleteAlbum() {
    console.info("Deleting Album")

    fetch(apiURL + "albums/delete/" + urlParams.get("album_id"), {
        method: 'delete'
    }).then(res => {
        if (res.status === 204) {
            window.location.href("/")
        } else {
            console.error(`Request failed ${res.body}`)
        }
    }).catch((error) => console.error(`Request failed ${error}`))
}

function updateAlbum() {
    console.info("Updating Album")

    let albumName = document.querySelector('#update-album-name').value;
    let albumCover = document.querySelector('#albumPic').value;

    fetch(apiURL + "albums/update/" + urlParams.get("album_id"), {
        method: 'post',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "name": albumName,
            "cover": albumCover
        })
    }).then(res => res.json())
        .then((data) => {
            fill(data, false, false)
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

let fill = (albumJSON, fillTracks, fillArtists) => {

    let albumNameText = document.querySelector('#albumName');
    albumNameText.innerHTML = albumNameText.innerHTML.replace("ALBUM NAME", albumJSON.name)

    let albumImage = document.getElementById("image1");
    albumImage.src = albumJSON.cover;
    console.info(albumJSON.cover)

    if (fillArtists) {
        let artists = document.getElementById("artistLinks");
        for (let i = 0; i < albumJSON.artists.length; i++) {
            let a = document.createElement('a');
            a.setAttribute('href', "/artists?artist_id=" + albumJSON.artists[i].id)
            a.innerHTML = albumJSON.artists[i].name;
            artists.append(a)
        }
    }

    if (fillTracks) {
        for (let i = 0; i < albumJSON.tracks.length; i++) {
            populate(albumJSON.tracks[i]);
        }
    }
}

let albumInfoFill = (albumId) => {
    fetch(apiURL + 'albums/read/' + albumId).then(res => res.json())
        .then((data) => {
            fill(data, true,true);
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

albumInfoFill(urlParams.get("album_id"));