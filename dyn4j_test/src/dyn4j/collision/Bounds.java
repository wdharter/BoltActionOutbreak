/*
 * Copyright (c) 2010-2021 William Bittle  http://www.dyn4j.org/
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions 
 *     and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *     and the following disclaimer in the documentation and/or other materials provided with the 
 *     distribution.
 *   * Neither the name of the copyright holder nor the names of its contributors may be used to endorse or 
 *     promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package dyn4j.collision;

import dyn4j.geometry.AABB;
import dyn4j.geometry.Rotatable;
import dyn4j.geometry.Shiftable;
import dyn4j.geometry.Transform;
import dyn4j.geometry.Translatable;
import dyn4j.geometry.Vector2;

/**
 * Represents the {@link Bounds} of a simulation.
 * <p>
 * By default all bounds are {@link Translatable} but not {@link Rotatable}.
 * <p>
 * Though not part of the bounds contract, a bounds object should only return true
 * from the {@link #isOutside(CollisionBody)} method when a {@link CollisionBody} is
 * <strong>fully</strong> outside the bounds.  This applies to the {@link #isOutside(AABB)}
 * and {@link #isOutside(AABB, Transform, Fixture)} methods as well.
 * <p>
 * NOTE: {@link #isOutside(AABB, Transform, Fixture)} method is called from the world implementations
 * to provide the most flexibility.  The {@link #isOutside(CollisionBody)} and {@link #isOutside(AABB)}
 * methods are NOT called internally.
 * @author William Bittle
 * @version 4.2.1
 * @since 1.0.0
 */
public interface Bounds extends Translatable, Shiftable {
	/**
	 * Returns the translation of the bounds.
	 * @return {@link Vector2}
	 * @since 3.2.0
	 */
	public abstract Vector2 getTranslation();
	
	/**
	 * Returns true if the given {@link CollisionBody} is <strong>fully</strong> outside the bounds.
	 * <p>
	 * If the {@link CollisionBody} contains zero {@link Fixture}s then 
	 * {@link CollisionBody} is considered to be outside the bounds.
	 * @param body the {@link CollisionBody} to test
	 * @return boolean true if outside the bounds
	 */
	public abstract boolean isOutside(CollisionBody<?> body);
	
	/**
	 * Returns true if the given {@link AABB} is <strong>fully</strong> outside the bounds.
	 * <p>
	 * If the {@link AABB} is a degenerate {@link AABB} (has zero area) then it's 
	 * considered to be outside the bounds.
	 * @param aabb the {@link AABB} to test
	 * @return boolean true if outside the bounds
	 * @since 4.0.0
	 */
	public abstract boolean isOutside(AABB aabb);

	/**
	 * Returns true if the given {@link AABB} or {@link Fixture} is <strong>fully</strong> outside the bounds.
	 * <p>
	 * The implementation can use any of the provided information.  For example, the {@link AxisAlignedBounds} class
	 * only uses the provided {@link AABB}.  For implementations of complex bounds, you can use the {@link Transform}
	 * and shape contained in the {@link Fixture}.
	 * @param aabb the {@link AABB} to test
	 * @param transform the {@link Transform} for the fixture
	 * @param fixture the {@link Fixture} (shape)
	 * @return boolean true if outside the bounds
	 * @since 4.2.1
	 */
	public abstract boolean isOutside(AABB aabb, Transform transform, Fixture fixture);
}
