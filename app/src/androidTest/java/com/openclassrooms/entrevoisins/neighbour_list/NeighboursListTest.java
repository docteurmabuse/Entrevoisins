
package com.openclassrooms.entrevoisins.neighbour_list;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.DetailNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    // This is fixed
    private static int FAVOURITE_COUNT = 2;



    private ListNeighbourActivity mActivity;
    private DetailNeighbourActivity mDetailActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);


    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        Intents.init();
        assertThat(mActivity, notNullValue());
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
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * We ensure that we show  detail activity when we click in recyclerview holder
     */
    @Test
    public void myNeighbourList_LaunchDetailActivity_isWorking(){
        // Given : We show detail of the element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform click on element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform((RecyclerViewActions.actionOnItemAtPosition(3, click())));
        // Then : the detail activity is shown
        intended(hasComponent(DetailNeighbourActivity.class.getName()));
    }
    /**
     * We ensure that the name of the use in detail activity is hte same than in the recyclerview holder
     */
    @Test
    public void DetailNameText_LaunchingDetailActivity_isWorking(){
        // Given : We show detail of the element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform click on element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform((RecyclerViewActions.actionOnItemAtPosition(3,
                click())));
        // Then : the detail activity is shown
        onView(withId(R.id.mDetailName)).check(matches(withText("Vincent")));
    }
    /**
     * We ensure that the name of the use in detail activity is hte same than in the recyclerview holder
     */
    @Test
    public void myFavouriteNeighbourList_LaunchingDetailActivity_isEqual(){
        // Given : We have 3 neighbour marked as favourite
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(withItemCount(0));
        // When perform click on element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform((RecyclerViewActions.actionOnItemAtPosition(3, click())));
        onView(ViewMatchers.withId(R.id.fab)).perform(click());
        Espresso.pressBack();

        onView(ViewMatchers.withId(R.id.list_neighbours)).perform((RecyclerViewActions.actionOnItemAtPosition(2, click())));
        onView(ViewMatchers.withId(R.id.fab)).perform(click());
        Espresso.pressBack();
        onView(ViewMatchers.withId(R.id.tabItem2)).perform((click()));


        // Then : the favorite list activity element is
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(withItemCount(FAVOURITE_COUNT));


    }

}