package com.openclassrooms.entrevoisins.neighbour_list;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.DetailNeighbourActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test class for Detail of neighbour
 */
@RunWith(AndroidJUnit4.class)
public class NeighbourDetailTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private DetailNeighbourActivity mDetailActivity;


    @Rule
    public ActivityTestRule<DetailNeighbourActivity> mDetailActivityRule = new ActivityTestRule(DetailNeighbourActivity.class);

    @Before
    public void setUp() {
        mDetailActivity = mDetailActivityRule.getActivity();
        assertThat(mDetailActivity, notNullValue());
    }

    /**
     * We ensure that we show  detail activity when we click in recyclerview holder
     */
    @Test
    public void myNeighbourList_shouldLaunchDetail_whenClicklaunchDetailNeighbour(){
        // Given : We show detail of the element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform click on element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform((RecyclerViewActions.actionOnItemAtPosition(3,
                click())));
        // Then : the detail activity is shown

        mDetailActivity = mDetailActivityRule.getActivity();
        assertThat(mDetailActivity, notNullValue());
    }

    /**
     * We ensure that the name of the use in detail activity is hte same than in the recyclerview holder
     */
    @Test
    public void DetailNameText_LaunchingDetailActivity_isEqual(){
        // Given : We show detail of the element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform click on element at position 3
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform((RecyclerViewActions.actionOnItemAtPosition(3,
                click())));
        // Then : the detail activity is shown

        onView(withId(R.id.mDetailName)).check(matches(withText("Chloé")));
    }
}
