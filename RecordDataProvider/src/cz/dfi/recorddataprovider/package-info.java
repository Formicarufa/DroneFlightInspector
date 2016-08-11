/**
 * An essential package for data sharing used by every component that needs data.
 * <p>
 * The most important is the {@link FileLookup} class.
 * Use {@link FileLookup#getDefault() } to get access to the data in the current
 * context (= currently selected file)
 * <p>
 * To be able to change the content of the currently selected file or to get 
 * {@link RecordFile}, which is a class that contains metadata about the currently
 * active file, use {@link FileLookupProvider#getDefault()}. 
 * <p>
 * 
 */
package cz.dfi.recorddataprovider;
