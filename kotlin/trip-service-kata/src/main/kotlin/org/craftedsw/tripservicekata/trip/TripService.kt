package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSession
import java.util.*

class TripService(val loggedUser: User?, val tripRepository: TripRepository) {

    fun getTripsByUser(user: User): List<Trip> {
        if (loggedUser != null) {
            for (friend in user.friends) {
                if (friend == loggedUser)
                    return tripRepository.findTripsFor(user)
            }
            return ArrayList()
        } else {
            throw UserNotLoggedInException()
        }
    }

}
