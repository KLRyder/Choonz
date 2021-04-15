function deleteGenre(){
    console.info("Deleting genre")

    fetch("http://localhost:8082/genres/delete/" + urlParams.get("genre_id"), {
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

function updateGenre(){
    console.info("Updating genre")

    let genreName = document.querySelector('#update-genre-name').value;

    fetch("http://localhost:8082/genres/update/" + urlParams.get("genre_id"), {
        method: 'put',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "name": genreName
        })
    }).then(res => res.json())
        .then((data) => {
            console.info("Updated")
            return;
        })
         .catch((error) => console.error(`Request failed ${error}`))
}

let fill = (genreJSON) => {

    let genreNameText = document.querySelector('#genreName');
    genreNameText.innerHTML = genreNameText.innerHTML.replace("GENRE NAME", genreJSON.name)

    document.getElementById("genreDesc").innerHTML = genreJSON.description;

    /* Get tracks by genre
    console.log(genreJSON)
    for (let i =0;i<albumJSON.tracks.length;i++) {
        populate(albumJSON.tracks[i]);
    }
    */

    for (let i =0;i<genreJSON.albums.length;i++) {
        displayAlbum(genreJSON.albums[i]);
    }

}

let genreInfoFill = (genreId) => {
    fetch(apiURL + 'genres/read/' + genreId).then(res => res.json())
        .then((data) => {
            fill(data);
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

genreInfoFill(urlParams.get("genre_id"));