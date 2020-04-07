package Message;

public enum MessageHeaders {
    error,
    good,

    //clients
    addClient,
    deleteClient,
    updateClient,
    getClients,
    filterClientsId,
    filterClientsName,

    //movies
    getMovies,
    addMovie,
    deleteMovie,
    updateMovie,
    filterEvenId,
    filterMoviesWithTitleLessThan,

    //rentals
    getAllRentals,
    addRental,
    updateRental,
    deleteRent,
    deleteClientCascade,
    deleteMovieCascade,
    getMostActiveClient,
    getMostRentedMovie,
    getRentedMoviesOfMostActiveClient,
}

