const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
getAlbumsByArtist(urlParams.get("artist_id"));