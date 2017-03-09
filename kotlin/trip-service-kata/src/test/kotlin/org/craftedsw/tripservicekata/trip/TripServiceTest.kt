package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*
import java.util.Arrays.asList


class TripServiceTest {

  @Rule @JvmField
  val thrown = ExpectedException.none()

  var loggedUser: User? = User()
  val subjectUser = User()
  val fakeTripList = ArrayList<Trip>()

  @Test
  fun assertIsWorking() {
    assertTrue(true)
  }

  @Test
  fun noLoggedUserTestCase_shouldThrowsRelatedException() {
    thrown.expect(UserNotLoggedInException::class.java)
    loggedUser = null
    getTrips()
  }

  @Test
  fun notFriendsUsersWithoutTripsUseCase() {
    loggedUser = User()
    assertTrue(getTrips().isEmpty())
  }

  @Test
  fun loggedUserFriendsOfSubjectUsersWithoutTripsUseCase() {
    loggedUser!!.addFriend(subjectUser)
    assertTrue(getTrips().isEmpty())
  }

  @Test
  fun subjectUserFriendsOfLoggedUsersWithoutTripsUseCase() {
    subjectUser.addFriend(loggedUser!!)
    assertTrue(getTrips().isEmpty())
  }

  @Test
  fun loggedUserFriendsOfSubjectUsersWithTripsUseCase() {
    loggedUser!!.addFriend(subjectUser)
    fakeTripList.addAll(asList(Trip(), Trip()))
    assertTrue(getTrips().isEmpty())
  }

  @Test
  fun subjectUserFriendsOfLoggedUsersWithTripsUseCase() {
    subjectUser.addFriend(loggedUser!!)
    fakeTripList.addAll(asList(Trip(), Trip()))
    assertEquals(fakeTripList, getTrips())
  }

  private fun getTrips(): List<Trip> {
    val fakeTripRepository = FakeTripRepository(fakeTripList)
    val service = TripService(
            loggedUser = loggedUser,
            tripRepository = fakeTripRepository
    )

    return service.getTripsByUser(subjectUser)
  }

}

class FakeTripRepository(val fakeTrips: List<Trip>) : TripRepository {
  override fun findTripsFor(user: User): List<Trip> {
    return fakeTrips
  }

}
