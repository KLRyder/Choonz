'use strict'

let fill = (albumJSON) => {

    let albumNameText = document.querySelector('#albumName');
    albumNameText.innerHTML = albumNameText.innerHTML.replace("ALBUM NAME", albumJSON.name)

    let albumImage = document.getElementById("image1");
    albumImage.src = albumJSON.cover;
    console.info(albumJSON.cover)

    let artists = document.getElementById("artistLinks");
    for (let i = 0; i < albumJSON.artists.length; i++) {
        let a = document.createElement('a');
        a.setAttribute('href', "/artists?artist_id=" + albumJSON.artists[i].id)
        a.innerHTML = albumJSON.artists[i].name;
        artists.append(a)
    }

    console.log(albumJSON)
    for (let i = 0; i < albumJSON.tracks.length; i++) {
        populate(albumJSON.tracks[i]);
    }

}

let albumInfoFill = (albumId) => {
    fetch(apiURL + 'albums/read/' + albumId).then(res => res.json())
        .then((data) => {
            fill(data);
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

albumInfoFill(urlParams.get("album_id"));