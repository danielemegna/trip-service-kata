package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import java.util.*

class TripService(val loggedUser: User?, val tripRepository: TripRepository) {

    fun getTripsByUser(subjectUser: User): List<Trip> {
        val loggedUser = loggedUser.let { it } ?: throw UserNotLoggedInException()
        val subjectUserHasLoggedUserAsFriend = subjectUser.friends.contains(loggedUser)

        if (subjectUserHasLoggedUserAsFriend)
            return tripRepository.findTripsFor(subjectUser)

        return ArrayList()
    }

}
