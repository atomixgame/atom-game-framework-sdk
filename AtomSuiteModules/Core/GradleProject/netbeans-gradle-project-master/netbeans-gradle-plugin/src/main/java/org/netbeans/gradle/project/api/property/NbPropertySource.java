package org.netbeans.gradle.project.api.property;

import javax.annotation.Nonnull;
import org.jtrim.property.PropertySource;
import org.netbeans.gradle.project.api.event.NbListenerRef;

/**
 * Defines the value of an arbitrary property. The value of this property might
 * change in an implementation dependent way. Some property might be changed by
 * client code, some might change due to external (and uncontrollable) events.
 * <P>
 * For example, the value of the property can be derived from the content of a
 * file and might get updated after the content of that file changes.
 * <P>
 * Note that this interface defines the same methods as the
 * {@code org.jtrim.property.PropertySource} (of the <I>JTrim</I> library)
 * interface with exactly the same contract. This is so, that later
 * (in NetBeans 7.4) this interface can extend from the interface of <I>JTrim</I>.
 *
 * <h3>Thread safety</h3>
 * Instances of this interface are required to be completely thread-safe
 * without any further synchronization.
 *
 * <h4>Synchronization transparency</h4>
 * Methods of this interface are required to be
 * <I>synchronization transparent</I> and may be called from any context.
 *
 * @param <ValueType> the type of the value of the property
 */
public interface NbPropertySource<ValueType> extends PropertySource<ValueType> {
    /**
     * {@inheritDoc }
     */
    @Override
    public ValueType getValue();

    /**
     * {@inheritDoc }
     */
    @Nonnull
    @Override
    public NbListenerRef addChangeListener(@Nonnull Runnable listener);
}