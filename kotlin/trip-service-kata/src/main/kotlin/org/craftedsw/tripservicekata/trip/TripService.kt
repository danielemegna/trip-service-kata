package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import java.util.*

class TripService(val loggedUser: User?, val tripRepository: TripRepository) {

    fun getTripsByUser(user: User): List<Trip> {
        var loggedUser = loggedUser.let { it } ?: throw UserNotLoggedInException()
        val userHasLoggedUserAsFriend = user.friends.contains(loggedUser)

        if (userHasLoggedUserAsFriend)
            return tripRepository.findTripsFor(user)

        return ArrayList()
    }

}
