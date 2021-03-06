/*
 * Copyright (c) 2010-2020 William Bittle  http://www.dyn4j.org/
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

/**
 * Main package containing general classes used by the library.
 * <p>
 * The {@link dyn4j.Version} class can be used to retrieve the version of the library that is
 * currently running.
 * <p>
 * The {@link dyn4j.Epsilon} class can be used to test near zero values (using an estimated
 * double precision value).
 * <p>
 * The library is designed to work with MKS (meters-kilograms-seconds) units.  Many default settings
 * in a variety of classes are defined based on the MKS unit system.  Use the {@link dyn4j.UnitConversion}
 * class to help convert from other units to MKS.
 * <p>
 * Many classes in the library implement the {@link dyn4j.DataContainer} interface.  This interface defines
 * a simple set of methods for store custom data along with the objects in the library.
 * <p>
 * The {@link dyn4j.BinarySearchTree} class is a generic implementation of an optionally self-balanced
 * binary tree and a supporting class to the library. Use the {@link dyn4j.BinarySearchTreeSearchCriteria} 
 * interface to perform custom searches on the tree.
 * <p>
 * The {@link dyn4j.Reference} class is another supporting class. This class is typically used
 * to create a member variable which encapsulates a reference to another piece of data, a primitive for
 * example to provide mutability on a single reference.
 * @author William Bittle
 * @version 4.0.0
 * @since 2.2.2
 */
package dyn4j;