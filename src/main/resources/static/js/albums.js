let deleteAlbumModal = document.getElementById("deleteAlbumModal");

function displayDeleteAlbumModal(){
    // Displays the confirmation modal
    console.log("Displaying delete album confirm modal")
    deleteAlbumModal.style.display = "block";
}

function closeDeleteAlbumModal(){
    // Displays the confirmation modal
    console.log("Closing delete album confirm modal")
    deleteAlbumModal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == deleteAlbumModal) {
        deleteAlbumModal.style.display = "none";
    }
  }