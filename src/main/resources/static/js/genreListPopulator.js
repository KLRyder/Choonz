'use strict'

let displayGenre = genreJSON => {
    let template = document.querySelector('#genreTemplate');
    let genreClone = template.content.cloneNode(true);

    let node = genreClone.getElementById("genre_GENRE-ID");
    node.id = 'genre-' + genreJSON.id;

    for (let i = 0; i < 7; i++) {
        node.innerHTML = node.innerHTML.replace("_GENRE-ID", genreJSON.id);
    }

    let genrename = genreJSON.name;
    let genreid = genreJSON.id;

    node.innerHTML = node.innerHTML.replace("_GENRE-ID", genreid)
        .replace("_GENRE-NAME", genrename).replace("genre name", genrename)

    document.getElementById('genreListAccordion').append(node);
}

let getAllGenres= () => {
    fetch(apiURL + 'genres/read').then(res => res.json())
        .then((data) => {
            for (let i = 0; i < data.length; i++) {
                displayGenre(data[i]);
            }
        })
        .catch((error) => console.error(`Request failed ${error}`))
}