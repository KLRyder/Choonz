'use strict'

let fill = (albumJSON) => {

    console.log("Try to fill in information")
    let albumNameText = document.querySelector('#albumName');
    albumNameText.innerHTML = albumNameText.innerHTML.replace("ALBUM NAME", albumJSON.name)

    let albumImage = document.getElementById("image1");
    albumImage.src = albumJSON.cover;

    let basicalbumInfo = document.getElementById("albumInfoRow");
    basicalbumInfo.innerHTML = basicalbumInfo.innerHTML.replace("_ARTIST-ID", albumJSON.artist.id)
    .replace("_ARTIST-NAME", albumJSON.artist.name)
}

let albumInfoFill = (albumId) => {
    console.log("try to read album by Id")
    fetch(apiURL + 'albums/read/' + albumId).then(res => res.json())
        .then((data) => {
            fill(data);
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

console.log("try to run script")
const queryString2 = window.location.search;
const urlParams2 = new URLSearchParams(queryString2);
albumInfoFill(urlParams2.get("album_id"));