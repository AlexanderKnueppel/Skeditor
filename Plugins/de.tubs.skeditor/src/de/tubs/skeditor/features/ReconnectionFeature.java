package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.impl.DefaultReconnectionFeature;

/**
 * For now reconnection is simply not allowed. Adding functionality similar to
 * SkillGraphAddEdgeFeature is possible but not immediately necessary
 * 
 * @author Enis
 *
 */
public class ReconnectionFeature extends DefaultReconnectionFeature {

	public ReconnectionFeature(IFeatureProvider fp) {
		super(fp);

	}

	@Override
	public boolean isAvailable(IContext context) {
		return false;
	}

	@Override
	public boolean canStartReconnect(IReconnectionContext context) {
		return false;
	}

	@Override
	public boolean canReconnect(IReconnectionContext context) {
		return false;
	}
}
