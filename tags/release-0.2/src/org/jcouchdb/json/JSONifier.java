package org.jcouchdb.json;

/**
 * Provides a method that converts another object into a JSON dump. See <a
 * href="http://json.org">JSON homepage</a> for details on JSON.
 * 
 * @author Sven Helmberger ( sven dot helmberger at gmx dot de )
 * @version $Id: JSONable.java,v 1.1 2005/12/30 15:54:02 fforw Exp $
 * @license BSD revised
 */
public interface JSONifier
{
    String toJSON(Object o);
}
