function deleteGenre() {
    console.info("Deleting genre")

    fetch("http://localhost:8082/genres/delete/" + urlParams.get("genre_id"), {
        method: 'delete'
    }).then(res => {
        if (res.status >= 200 && res.status <= 300) {
            console.info("Deleted successfully")
            window.location.href = "/";
        } else {
            console.error(`Request failed ${res.body}`)
        }
    }).catch((error) => console.error(`Request failed ${error}`))
}

function updateGenre() {
    console.info("Updating genre")

    let genreName = document.querySelector('#update-genre-name').value;
    let genreDesc = document.querySelector('#update-genre-description').value;

    fetch("http://localhost:8082/genres/update/" + urlParams.get("genre_id"), {
        method: 'post',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "name": genreName,
            "description": genreDesc
        })
    }).then(res => res.json())
        .then((data) => {
            fill(data, false)
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

let fill = (genreJSON, fillArtist) => {

    let genreNameText = document.querySelector('#genreName');
    genreNameText.innerHTML = genreJSON.name;

    document.getElementById("genreDesc").innerHTML = genreJSON.description;

    /* Get tracks by genre
    console.log(genreJSON)
    for (let i =0;i<albumJSON.tracks.length;i++) {
        populate(albumJSON.tracks[i]);
    }
    */

    if (fillArtist) {
        for (let i = 0; i < genreJSON.albums.length; i++) {
            displayAlbum(genreJSON.albums[i]);
        }
    }

}

let genreInfoFill = (genreId) => {
    fetch(apiURL + 'genres/read/' + genreId).then(res => res.json())
        .then((data) => {
            fill(data, true);
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

genreInfoFill(urlParams.get("genre_id"));