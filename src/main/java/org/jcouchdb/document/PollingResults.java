package org.jcouchdb.document;

import java.util.List;

import org.svenson.JSONProperty;
import org.svenson.JSONTypeHint;

public class PollingResults
{
    private long lastSequence;
    
    private long pending;
    
    private List<ChangeNotification> results;

    public long getLastSequence()
    {
        return lastSequence;
    }

    @JSONProperty("last_seq")
    public void setLastSequence(long lastSequence)
    {
        this.lastSequence = lastSequence;
    }

    /**
	 * @return the pending
	 */
	public long getPending() {
		return pending;
	}

	/**
	 * @param pending the pending to set
	 */
	@JSONProperty("pending")
	public void setPending(long pending) {
		this.pending = pending;
	}

	@JSONTypeHint(ChangeNotification.class)
    public List<ChangeNotification> getResults()
    {
        return results;
    }

    public void setResults(List<ChangeNotification> results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return super.toString() + "[lastSequence=" + lastSequence + ", results=" + results + "]";
    }
}
