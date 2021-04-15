let searchContainer = document.querySelector('#albumListContainer');
let msnSearch = new Masonry(searchContainer, {
    // options
    percentPosition: true,
    itemSelector: '.msn-list'
});
let displaySearch = searchJSON => {
    console.log(searchJSON)
    for (let i = 0; i < searchJSON.artists.length; i++) {
        displayArtist(searchJSON.artists[i]);
    }
    for (let i = 0; i < searchJSON.albums.length; i++) {
        displayAlbum(searchJSON.albums[i]);
    }
    for (let i = 0; i < searchJSON.tracks.length; i++) {
        populate(searchJSON.tracks[i]);
    }
    for (let i = 0; i < searchJSON.playlists.length; i++) {
        displayPlaylist(searchJSON.playlists[i]);
    }
    for (let i = 0; i < searchJSON.genres.length; i++) {
        displayGenre(searchJSON.genres[i]);
    }
    msnSearch.layout();
}

let search = (term, strict) =>{
    fetch(apiURL + 'search?term='+term+'&strict='+strict).then(res => res.json())
        .then((data) => {
                displaySearch(data);
        })
        .catch((error) => console.error(`Request failed ${error}`))
}

let msnSetup = () =>{
    msnSearch.appended(document.getElementById("artistList"));
    msnSearch.appended(document.getElementById("trackList"));
    msnSearch.appended(document.getElementById("albumList"));
    msnSearch.appended(document.getElementById("genreList"));
    msnSearch.appended(document.getElementById("playlistList"));
    msnSearch.layout();
}
msnSetup();
if (urlParams.has("term")) {
    search(urlParams.get("term"),false);
} else {
    location.href="/";
}

