
tell application "Finder"
	set this_file to (container of (path to me) as text) & "spotify.log"
end tell

my write_to_file("start
", this_file, true)

set old_output to ""


repeat
	
	tell application "System Events"
		set myList to (name of every process)
	end tell
	if myList contains "Spotify" then
		tell application "Spotify"
			if player state is stopped then
				set output to "Stopped"
			else
				set trackname to name of current track
				set artistname to artist of current track
				set albumname to album of current track
				set track_id to id of current track
				if player state is playing then
					set output to "playing|" & track_id & "|" & trackname & "|" & artistname & "|" & albumname
				else if player state is paused then
					set output to "paused|" & track_id & "|" & trackname & "|" & artistname & "|" & albumname
				end if
			end if
		end tell
	else
		set output to "spotify is not running"
	end if
	
	delay 1
	
	if output is not equal to old_output then
		my write_to_file(output & "\n", this_file, true)
		set old_output to output
	end if
	
end repeat



on write_to_file(this_data, target_file, append_data) -- (string, file path as string, boolean)
	try
		set the target_file to the target_file as text
		set the open_target_file to open for access file target_file with write permission
		if append_data is false then
			set eof of the open_target_file to 0
		end if
		write this_data to the open_target_file as «class utf8» starting at eof
		close access the open_target_file
		return true
	on error errmsg
		try
			close access file target_file
		end try
		return false
	end try
end write_to_file