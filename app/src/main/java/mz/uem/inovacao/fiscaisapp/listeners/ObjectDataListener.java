package mz.uem.inovacao.fiscaisapp.listeners;

import java.util.List;

/**
 * This interface allows OccurrenceListFragment and OccurrenceMapFragment to be notified of
 * changes to the Occurrences they should be displayed.
 */
public interface ObjectDataListener {

    /**
     * This method is called when the whole list of occurrences is retrieved from the cloud
     *
     */
    void onObjectListChanged(List<?> objects);

    /**
     * This method is called when the user has just registered an Occurrence to the cloud.
     */
    void onNewObjectAdded(Object object);

}
