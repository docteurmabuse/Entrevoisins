
package com.openclassrooms.entrevoisins.neighbour_list;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.DetailNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.ChildAtPosition.childAtPosition;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    // This is fixed
    private static int FAVOURITE_COUNT = 1;


    private ListNeighbourActivity mActivity;
    private Neighbour fakeDetailNeighbour;

    public static final String DETAIL_NEIGHBOUR = "detailNeighbour";


    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);
    public ActivityTestRule<DetailNeighbourActivity> detailNeighbourActivityTestRule =
            new ActivityTestRule(DetailNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        Intents.init();
        fakeDetailNeighbour = new Neighbour(3, "Chloé", "http://i.pravatar.cc/150?u=a042581f4e29026704f");
    }

    @After
    public void cleanUp() {
        Intents.release();
    }


    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * We ensure that we show  detail activity when we click in recyclerview holder
     */
    @Test
    public void myNeighbourList_LaunchDetailActivity_isWorking() {
        // Given : We show detail of the element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform click on element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform((RecyclerViewActions.actionOnItemAtPosition(3, click())));
        // Then : the detail activity is shown
        intended(hasComponent(DetailNeighbourActivity.class.getName()));
    }

    /**
     * We ensure that the name of the neighbour  in detail activity is the same than in the recyclerview holder
     */
    @Test
    public void DetailNameText_LaunchingDetailActivity_isWorking() {
        // Given : We show detail of the element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform click on element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform((RecyclerViewActions.actionOnItemAtPosition(3,
                click())));
        // Then : the detail activity is shown
        onView(withId(R.id.mDetailName)).check(matches(withText("Vincent")));
    }

    /**
     * We ensure that the recyclerview favorite neighbour list have one item after adding a favorite
     */
    @Test
    public void myNeighbourDetail_checkIfAddingFavoriteIsWorking() {
        // Given : We have no favorite neighbours when starting app.
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(withItemCount(0));
        // When perform click on element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform((RecyclerViewActions.actionOnItemAtPosition(3, click())));
        onView(ViewMatchers.withId(R.id.fab)).perform(click());
        Espresso.pressBack();
        // Then : We should have 1 favorite neighbour in the list
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(withItemCount(FAVOURITE_COUNT));
    }


    /**
     * We ensure that fab snack have favorite confirmation message on click
     */
    @Test
    public void myNeighbourDetail_TestFabSnack() {
        // Given : We launch DetailNeighbourActivity with fake neighbour.
        Intent intent = new Intent();
        intent.putExtra(DETAIL_NEIGHBOUR, fakeDetailNeighbour);
        detailNeighbourActivityTestRule.launchActivity(intent);
        // When perform click on fab to add favorite neighbour
        onView(withId(R.id.fab)).perform(click());
        // Then : We should have confirmation message on the snack
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Vous venez d'ajouter Chloé à vos voisins favoris!")))
                .check(matches(isDisplayed()));
    }

    /**
     * We ensure that we delete a favorite, the favorite is delete from favorite neighbour list but still in the neighbour list
     */
    @Test
    public void myFavoriteNeighbourList_deleteAction_shouldRemoveItem() {
        // Given : We add 1 favorite neighbour and after remove the element at position 1
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(withItemCount(0));
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform((RecyclerViewActions.actionOnItemAtPosition(3, click())));
        onView(ViewMatchers.withId(R.id.fab)).perform(click());
        Espresso.pressBack();
        Espresso.onView(ViewMatchers.withId(R.id.tabItem2)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(withItemCount(FAVOURITE_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(withItemCount(FAVOURITE_COUNT - 1));
    }

    /**
     * We ensure that we delete a favorite, the favorite is delete from favorite neighbour list but still in the neighbour list
     */
    @Test
    public void myFavoriteNeighbourList_testFavoriteTab_shouldBeEmpty() {
        // Given : We add 1 favorite neighbour and after remove the element at position 1
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        onView(ViewMatchers.withId(R.id.tabItem2)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(withItemCount(0));
    }

    @Test
    public void myFavoriteNeighboursList__deleteAction_shouldRemoveItem() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.list_neighbours),
                        withParent(withId(R.id.container))));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                allOf(withId(R.id.toolbar_layout), withContentDescription("Chloé")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.list_neighbours),
                        withParent(withId(R.id.container))));
        recyclerView2.perform(actionOnItemAtPosition(4, click()));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                allOf(withId(R.id.toolbar_layout), withContentDescription("Elodie")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction viewPager = onView(
                allOf(withId(R.id.container),
                        childAtPosition(
                                allOf(withId(R.id.main_content),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        viewPager.perform(swipeLeft());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.item_list_delete_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_favorite_neighbours),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.list_favorite_neighbours),
                        withParent(allOf(withId(R.id.container),
                                childAtPosition(
                                        withId(R.id.main_content),
                                        1))),
                        isDisplayed()));
        recyclerView3.check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(withItemCount(2 - 1));

    }
}