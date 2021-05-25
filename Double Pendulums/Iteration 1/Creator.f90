module Updater
    implicit none
    
contains
    subroutine PositionUpdater(t1, t2, w1, w2)
    implicit none
        real :: t1, t2, w1, w2, a1, a2, m1, m2, l1, l2, g, dt
        m1 = 1
        m2 = 1
        l1 = 1
        l2 = 1
        g = 9.81
        dt = 0.005
        a1 = (m2*g*sin(t2)*cos(t1-t2)) - (m2*sin(t1-t2))*(l1*w1*w1*cos(t1-t2)+l2*w2*w2) - (m1+m2)*g*sin(t1)
        a1 = a1/(l1*(m1+m2*sin(t1-t2)*sin(t1-t2)))
        a2 = (m1+m2) * (l1*w1*w1*sin(t1-t2)-g*sin(t2)+g*sin(t1)*cos(t1-t2)) + m2*l2*w2*w2*sin(t1-t2)*cos(t1-t2)
        a2 = a2/(l2*(m1+m2*sin(t1-t2)*sin(t1-t2)))
        w1 = w1 + a1 * dt
        w2 = w2 + a2 * dt
        t1 = t1 + w1 * dt
        t2 = t2 + w2 * dt
    end subroutine PositionUpdater
end module Updater


program Creator
    use Updater
    implicit none
        integer :: i, j, k
        real, dimension(4,6000) :: Matrix
        real, dimension(128,128,3) :: M0
        real, dimension(128,128,3) :: M1
        real, dimension(128,128,3) :: M2
        real :: pi = 4.0 * atan(1.0)
        open (unit = 1, file = "1.txt", status = 'old')
        do i = 1, 128
            do j = 1, 128
                read (1,*,end = 10) (M0(i, j, k),  k = 1, 3) !Taking input of matrix
            end do
        end do
        10 close (unit = 1)

        do i = 1, 128
            do j = 1, 128
                do k = 1, 3
                    M1(i,j,k) = M0(i,j,k)
                end do
            end do
        end do
        Matrix(1,1) = 180.0 * (pi / 180.0)
        Matrix(2,1) = 90.0 * (pi / 180.0)
        Matrix(3,1) = 0.0
        Matrix(4,1) = 0.0
        do i = 2, 6000
            Matrix(1, i) = Matrix(1, (i-1))
            Matrix(2, i) = Matrix(2, (i-1))
            Matrix(3, i) = Matrix(3, (i-1))
            Matrix(4, i) = Matrix(4, (i-1))
            call PositionUpdater(Matrix(1,i), Matrix(2,i), Matrix(3,i), Matrix(4,i))
            if ( Matrix(1, i) > pi ) then
                Matrix(1, i) = -2 * pi + Matrix(1, i)
            end if
            if ( Matrix(1, i) < (-1 * pi) ) then
                Matrix(1, i) = 2 * pi + Matrix(1, i)
            end if
            if ( Matrix(2, i) > pi ) then
                Matrix(2, i) = -2 * pi + Matrix(2, i)
            end if
            if ( Matrix(2, i) < (-1 * pi) ) then
                Matrix(2, i) = 2 * pi + Matrix(2, i)
            end if
        end do
        open(unit = 1, file="OutputColours.txt")
        do i = 1, 6000
            !write (1, *) (Matrix(1, i) * (180.0 / pi)), (Matrix(2, i) * (180.0 / pi))
        end do

        do i = 1, 6000
            write (1, *)  M0(int((128.0 * (pi + Matrix(1,i))) / (2 * pi)), int((128 * (pi + Matrix(2,i)) / (2 * pi))), 1)
            write (1, *)  M0(int((128.0 * (pi + Matrix(1,i))) / (2 * pi)), int((128 * (pi + Matrix(2,i)) / (2 * pi))), 2)
            write (1, *)  M0(int((128.0 * (pi + Matrix(1,i))) / (2 * pi)), int((128 * (pi + Matrix(2,i)) / (2 * pi))), 3)
        end do
        close (unit = 1)

end program Creator