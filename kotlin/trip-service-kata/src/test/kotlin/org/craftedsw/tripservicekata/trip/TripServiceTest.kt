package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*


class TripServiceTest {

  @Rule @JvmField
  val thrown = ExpectedException.none()

  @Test
  fun assertIsWorking() {
    assertTrue(true)
  }

  @Test
  fun noLoggedUserTestCase_shouldThrowsRelatedException() {
    thrown.expect(UserNotLoggedInException::class.java)

    val aNullUser = null
    val service = buildService(aNullUser, ArrayList())

    service.getTripsByUser(User())
  }

  @Test
  fun notFriendsUsersWithoutTripsUseCase() {
    val loggedUser = User()
    val anotherUser = User()
    val service = buildService(loggedUser, ArrayList())

    val trips = service.getTripsByUser(anotherUser)

    assertTrue(trips.isEmpty())
  }

  @Test
  fun loggedUserFriendsOfOtherUsersWithoutTripsUseCase() {
    val loggedUser = User()
    val anotherUser = User()
    val service = buildService(loggedUser, ArrayList())
    loggedUser.addFriend(anotherUser)

    val trips = service.getTripsByUser(anotherUser)

    assertTrue(trips.isEmpty())
  }

  @Test
  fun otherUserFriendsOfLoggedUsersWithoutTripsUseCase() {
    val loggedUser = User()
    val anotherUser = User()
    val service = buildService(loggedUser, ArrayList())
    anotherUser.addFriend(loggedUser)
    
    val trips = service.getTripsByUser(anotherUser)

    assertTrue(trips.isEmpty())
  }

  private fun buildService(loggedUser: User?, tripList: ArrayList<Trip>): TripService {
    val fakeTripRepository = FakeTripRepository(tripList)
    return TripService(
            loggedUser = loggedUser,
            tripRepository = fakeTripRepository
    )
  }
}

class FakeTripRepository(val fakeTrips: List<Trip>) : TripRepository {
  override fun findTripsFor(user: User): List<Trip> {
    return fakeTrips
  }

}
